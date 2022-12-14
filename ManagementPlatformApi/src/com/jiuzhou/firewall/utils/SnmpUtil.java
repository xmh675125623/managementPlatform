package com.jiuzhou.firewall.utils;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivAES128;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
* @author xingmh
* @version 创建时间：2020年9月8日 下午3:40:04
* 类说明
*/
public class SnmpUtil {

    public static Snmp snmp = null;
    private static String community = "public";

    /**
     * @description 初始化snmp
     * @throws IOException
     */
    public static Snmp initSnmp() throws IOException{
            //1、初始化多线程消息转发类
            MessageDispatcher messageDispatcher = new MessageDispatcherImpl();
            //其中要增加三种处理模型。如果snmp初始化使用的是Snmp(TransportMapping<? extends Address> transportMapping) ,就不需要增加
            messageDispatcher.addMessageProcessingModel(new MPv1());
            messageDispatcher.addMessageProcessingModel(new MPv2c());
            //当要支持snmpV3版本时，需要配置user
            OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());
            USM usm = new USM(SecurityProtocols.getInstance().addDefaultProtocols(), localEngineID, 0);
            UsmUser user = new UsmUser(new OctetString("SNMPV3"), AuthSHA.ID, new OctetString("authPassword"),
                PrivAES128.ID, new OctetString("privPassword"));
            usm.addUser(user.getSecurityName(), user);
            messageDispatcher.addMessageProcessingModel(new MPv3(usm));
            //2、创建transportMapping
//            UdpAddress updAddr = (UdpAddress) GenericAddress.parse("udp:192.168.0.8/161");
            TransportMapping<?> transportMapping = new DefaultUdpTransportMapping();
            //3、正式创建snmp
            Snmp snmp = new Snmp(messageDispatcher, transportMapping);
            //开启监听
            snmp.listen();
            
            return snmp;
    }
    
	public static Target createTarget(String ipAddress, int version, int port) {
        Target target = null;
        if (!(version == SnmpConstants.version3 || version == SnmpConstants.version2c || version == SnmpConstants.version1)) {
            System.out.println("参数version异常");
            return target;
        }
        if (version == SnmpConstants.version3) {
            target = new UserTarget();
            //snmpV3需要设置安全级别和安全名称，其中安全名称是创建snmp指定user设置的new OctetString("SNMPV3")
            target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
            target.setSecurityName(new OctetString("SNMPV3"));
        } else {
            //snmpV1和snmpV2需要指定团体名名称
            target = new CommunityTarget();
            ((CommunityTarget)target).setCommunity(new OctetString(community));
            if (version == SnmpConstants.version2c) {
                target.setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv2c);
            }
        }
        target.setVersion(version);
        //必须指定，没有设置就会报错。
        target.setAddress(GenericAddress.parse("udp:"+ipAddress+"/"+port));
        target.setRetries(5);
        target.setTimeout(3000);
        return target;
    }
    
    
    public static PDU createPDU(int version, int type, String oid){
        PDU pdu = null;
        if (version == SnmpConstants.version3) {
            pdu = new ScopedPDU();
        }else {
            pdu = new PDUv1();
        }
        pdu.setType(type);
        //可以添加多个变量oid
        pdu.add(new VariableBinding(new OID(oid)));
        return pdu;
    }
    
    public static void snmpGet(String oid){
        try {
            //1、初始化snmp,并开启监听
            snmp = initSnmp();
            //2、创建目标对象
            Target target = createTarget("192.168.0.220",SnmpConstants.version2c, SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
            //3、创建报文
            PDU pdu = createPDU(SnmpConstants.version2c, PDU.GET, oid);
            System.out.println("-------> 发送PDU <-------");
            //4、发送报文，并获取返回结果
            ResponseEvent responseEvent = snmp.send(pdu, target);
            PDU response = responseEvent.getResponse();
            System.out.println("返回结果：" + response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        snmpGet("1.3.6.1.4.1.2021.4.5.0");
    }
    
    public static void snmpWalk(String oid) {
        try {
            //1、初始化snmp,并开启监听
            snmp = initSnmp();
            //2、创建目标对象
            Target target = createTarget("192.168.0.220", SnmpConstants.version2c, SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
            //3、创建报文
            PDU pdu = createPDU(SnmpConstants.version2c, PDU.GETNEXT, oid);
            System.out.println("-------> 发送PDU <-------");
            //4、发送报文，并获取返回结果
            boolean matched = true;
            while (matched) {
                ResponseEvent responseEvent = snmp.send(pdu, target);
                if (responseEvent == null || responseEvent.getResponse() == null) {
                    break;
                }
                PDU response = responseEvent.getResponse();
                String nextOid = null;
                Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
                for (int i = 0; i < variableBindings.size(); i++) {
                    VariableBinding variableBinding = variableBindings.elementAt(i);
                    Variable variable = variableBinding.getVariable();
                    nextOid = variableBinding.getOid().toDottedString();
                    //如果不是这个节点下的oid则终止遍历，否则会输出很多，直到整个遍历完。
                    if (!nextOid.startsWith(oid)) {
                        matched = false;
                        break;
                    }
//                    System.out.println(variableBinding.getOid().toString());
//                    System.out.println(variable);
                }
                if (!matched) {
                    break;
                }
                pdu.clear();
                pdu.add(new VariableBinding(new OID(nextOid)));
                System.out.println("返回结果：" + response);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
//    public static void main(String[] args) {
//    	snmpWalk("1.3.6.1.4.1.2021.9.1.2");
//    	snmpWalk("1.3.6.1.4.1.2021.9.1.6.1");
//    	snmpWalk("1.3.6.1.4.1.2021.9.1.8.1");
//    	
//	}
    
}
