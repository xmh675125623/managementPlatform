import axios from 'axios'
import util from '@/libs/util'

// const dataGetUrl = `http://${window.document.domain}:9200/_search`
const dataGetUrl = 'https://' + util.cookies.get('hostIndex') + ':19200/_search'
const time = 1000 * 60 * 60 * 6
const requestTimeout = 2000

export function NETWORK_PROTOCOL_DATA_GET () {
  return axios({
    method: 'post',
    timeout: requestTimeout,
    url: dataGetUrl,
    headers: { 'Content-Type': 'application/json' },
    data: {
      aggs: {
        2: {
          terms: {
            field: 'network.protocol',
            order: {
              _count: 'desc'
            },
            size: 30
          }
        }
      },
      size: 0,
      stored_fields: [
        '*'
      ],
      script_fields: {},
      docvalue_fields: [
        {
          field: '@timestamp',
          format: 'date_time'
        },
        {
          field: 'cert.notAfter',
          format: 'date_time'
        },
        {
          field: 'cert.notBefore',
          format: 'date_time'
        },
        {
          field: 'event.created',
          format: 'date_time'
        },
        {
          field: 'event.end',
          format: 'date_time'
        },
        {
          field: 'event.ingested',
          format: 'date_time'
        },
        {
          field: 'event.start',
          format: 'date_time'
        },
        {
          field: 'file.accessed',
          format: 'date_time'
        },
        {
          field: 'file.created',
          format: 'date_time'
        },
        {
          field: 'file.ctime',
          format: 'date_time'
        },
        {
          field: 'file.mtime',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'firstPacket',
          format: 'date_time'
        },
        {
          field: 'lastPacket',
          format: 'date_time'
        },
        {
          field: 'package.installed',
          format: 'date_time'
        },
        {
          field: 'process.parent.start',
          format: 'date_time'
        },
        {
          field: 'process.start',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.from',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.till',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.org_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.rec_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.ref_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.xmt_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.nextUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.revoketime',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.thisUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.pe.compile_ts',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_accessed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_changed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_created',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_modified',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.snmp.up_since',
          format: 'date_time'
        },
        {
          field: 'zeek.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_after',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_before',
          format: 'date_time'
        }
      ],
      _source: {
        excludes: []
      },
      query: {
        bool: {
          must: [],
          filter: [
            {
              bool: {
                must_not: {
                  bool: {
                    should: [
                      {
                        bool: {
                          should: [
                            {
                              match: {
                                tags: 'ics'
                              }
                            }
                          ],
                          minimum_should_match: 1
                        }
                      },
                      {
                        bool: {
                          should: [
                            {
                              match: {
                                tags: 'ics_best_guess'
                              }
                            }
                          ],
                          minimum_should_match: 1
                        }
                      }
                    ],
                    minimum_should_match: 1
                  }
                }
              }
            },
            {
              range: {
                firstPacket: {
                  // gte: '2022-08-25T02:23:37.326Z',
                  // lte: '2022-08-25T08:23:37.326Z',
                  gte: new Date().getTime() - (time),
                  lte: new Date().getTime(),
                  format: 'epoch_millis'
                }
              }
            }
          ],
          should: [],
          must_not: []
        }
      }
    }
  })
}

export function EVENT_SEVERITY_DATA_GET () {
  return axios({
    method: 'post',
    timeout: requestTimeout,
    url: dataGetUrl,
    headers: { 'Content-Type': 'application/json' },
    data: {
      aggs: {
        2: {
          range: {
            field: 'event.severity',
            ranges: [
              {
                from: 1,
                to: 10
              },
              {
                from: 10,
                to: 20
              },
              {
                from: 20,
                to: 30
              },
              {
                from: 30,
                to: 40
              },
              {
                from: 40,
                to: 50
              },
              {
                from: 50,
                to: 60
              },
              {
                from: 60,
                to: 70
              },
              {
                from: 80,
                to: 90
              },
              {
                from: 90,
                to: 100
              },
              {
                from: 100
              }
            ],
            keyed: true
          },
          aggs: {
            4: {
              histogram: {
                field: 'event.severity',
                interval: 10,
                min_doc_count: 0,
                extended_bounds: {
                  min: 0,
                  max: 101
                }
              }
            }
          }
        }
      },
      size: 0,
      stored_fields: [
        '*'
      ],
      script_fields: {},
      docvalue_fields: [
        {
          field: '@timestamp',
          format: 'date_time'
        },
        {
          field: 'cert.notAfter',
          format: 'date_time'
        },
        {
          field: 'cert.notBefore',
          format: 'date_time'
        },
        {
          field: 'event.created',
          format: 'date_time'
        },
        {
          field: 'event.end',
          format: 'date_time'
        },
        {
          field: 'event.ingested',
          format: 'date_time'
        },
        {
          field: 'event.start',
          format: 'date_time'
        },
        {
          field: 'file.accessed',
          format: 'date_time'
        },
        {
          field: 'file.created',
          format: 'date_time'
        },
        {
          field: 'file.ctime',
          format: 'date_time'
        },
        {
          field: 'file.mtime',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'firstPacket',
          format: 'date_time'
        },
        {
          field: 'lastPacket',
          format: 'date_time'
        },
        {
          field: 'package.installed',
          format: 'date_time'
        },
        {
          field: 'process.parent.start',
          format: 'date_time'
        },
        {
          field: 'process.start',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.from',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.till',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.org_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.rec_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.ref_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.xmt_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.nextUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.revoketime',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.thisUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.pe.compile_ts',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_accessed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_changed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_created',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_modified',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.snmp.up_since',
          format: 'date_time'
        },
        {
          field: 'zeek.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_after',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_before',
          format: 'date_time'
        }
      ],
      _source: {
        excludes: []
      },
      query: {
        bool: {
          must: [
            {
              query_string: {
                query: 'event.dataset:*',
                analyze_wildcard: true,
                time_zone: 'Asia/Shanghai'
              }
            }
          ],
          filter: [
            {
              match_all: {}
            },
            {
              range: {
                firstPacket: {
                  gte: new Date().getTime() - (time),
                  lte: new Date().getTime(),
                  format: 'epoch_millis'
                }
              }
            }
          ],
          should: [],
          must_not: []
        }
      }
    }
  })
}

export function CLIENT_IP_DATA_GET () {
  return axios({
    method: 'post',
    timeout: requestTimeout,
    url: dataGetUrl,
    headers: { 'Content-Type': 'application/json' },
    data: {
      aggs: {
        2: {
          terms: {
            field: 'client.ip',
            order: {
              _count: 'desc'
            },
            size: 99
          },
          aggs: {
            3: {
              terms: {
                field: 'client.ip',
                order: {
                  _count: 'desc'
                },
                size: 5
              }
            }
          }
        }
      },
      size: 0,
      stored_fields: [
        '*'
      ],
      script_fields: {},
      docvalue_fields: [
        {
          field: '@timestamp',
          format: 'date_time'
        },
        {
          field: 'eve.flow.end',
          format: 'date_time'
        },
        {
          field: 'eve.flow.start',
          format: 'date_time'
        },
        {
          field: 'eve.stats.detect.engines.last_reload',
          format: 'date_time'
        },
        {
          field: 'eve.timestamp',
          format: 'date_time'
        },
        {
          field: 'eve.tls.notafter',
          format: 'date_time'
        },
        {
          field: 'eve.tls.notbefore',
          format: 'date_time'
        },
        {
          field: 'event.created',
          format: 'date_time'
        },
        {
          field: 'event.end',
          format: 'date_time'
        },
        {
          field: 'event.ingested',
          format: 'date_time'
        },
        {
          field: 'event.start',
          format: 'date_time'
        },
        {
          field: 'file.accessed',
          format: 'date_time'
        },
        {
          field: 'file.created',
          format: 'date_time'
        },
        {
          field: 'file.ctime',
          format: 'date_time'
        },
        {
          field: 'file.mtime',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'package.installed',
          format: 'date_time'
        },
        {
          field: 'process.parent.start',
          format: 'date_time'
        },
        {
          field: 'process.start',
          format: 'date_time'
        },
        {
          field: 'tls.client.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.client.not_before',
          format: 'date_time'
        },
        {
          field: 'tls.client.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.client.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'tls.server.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.server.not_before',
          format: 'date_time'
        },
        {
          field: 'tls.server.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.server.x509.not_before',
          format: 'date_time'
        }
      ],
      _source: {
        excludes: []
      },
      query: {
        bool: {
          must: [
            {
              match_all: {}
            }
          ],
          filter: [
            {
              match_phrase: {
                'client.as.system': {
                  query: 'private'
                }
              }
            },
            {
              bool: {
                should: [
                  {
                    exists: {
                      field: 'rep_tags'
                    }
                  },
                  {
                    match_phrase: {
                      'eve.event_type': 'alert'
                    }
                  }
                ],
                minimum_should_match: 1
              }
            },
            {
              range: {
                '@timestamp': {
                  gte: new Date().getTime() - (time),
                  lte: new Date().getTime(),
                  format: 'epoch_millis'
                }
              }
            }
          ],
          should: [],
          must_not: [
            {
              match_phrase: {
                'server.as.system': {
                  query: 'private'
                }
              }
            }
          ]
        }
      }
    }
  })
}

export function DATE_HISTOGRAM_DATA_GET () {
  return axios({
    method: 'post',
    timeout: requestTimeout,
    url: dataGetUrl,
    headers: { 'Content-Type': 'application/json' },
    data: {
      aggs: {
        2: {
          date_histogram: {
            field: 'firstPacket',
            fixed_interval: '5m',
            time_zone: 'Asia/Shanghai',
            min_doc_count: 1
          }
        }
      },
      size: 0,
      stored_fields: [
        '*'
      ],
      script_fields: {},
      docvalue_fields: [
        {
          field: '@timestamp',
          format: 'date_time'
        },
        {
          field: 'cert.notAfter',
          format: 'date_time'
        },
        {
          field: 'cert.notBefore',
          format: 'date_time'
        },
        {
          field: 'event.created',
          format: 'date_time'
        },
        {
          field: 'event.end',
          format: 'date_time'
        },
        {
          field: 'event.ingested',
          format: 'date_time'
        },
        {
          field: 'event.start',
          format: 'date_time'
        },
        {
          field: 'file.accessed',
          format: 'date_time'
        },
        {
          field: 'file.created',
          format: 'date_time'
        },
        {
          field: 'file.ctime',
          format: 'date_time'
        },
        {
          field: 'file.mtime',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'firstPacket',
          format: 'date_time'
        },
        {
          field: 'lastPacket',
          format: 'date_time'
        },
        {
          field: 'package.installed',
          format: 'date_time'
        },
        {
          field: 'process.parent.start',
          format: 'date_time'
        },
        {
          field: 'process.start',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.from',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.till',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.org_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.rec_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.ref_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.xmt_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.nextUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.revoketime',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.thisUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.pe.compile_ts',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_accessed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_changed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_created',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_modified',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.snmp.up_since',
          format: 'date_time'
        },
        {
          field: 'zeek.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_after',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_before',
          format: 'date_time'
        }
      ],
      _source: {
        excludes: []
      },
      query: {
        bool: {
          must: [
            {
              query_string: {
                analyze_wildcard: true,
                default_field: '*',
                query: '*',
                time_zone: 'Asia/Shanghai'
              }
            },
            {
              query_string: {
                query: 'event.dataset:*',
                analyze_wildcard: true,
                time_zone: 'Asia/Shanghai'
              }
            }
          ],
          filter: [
            {
              match_all: {}
            },
            {
              range: {
                firstPacket: {
                  gte: new Date().getTime() - (time),
                  lte: new Date().getTime(),
                  format: 'epoch_millis'
                }
              }
            }
          ],
          should: [],
          must_not: []
        }
      }
    }
  })
}

/**
 * 外部攻击者
 * @returns {AxiosPromise}
 * @constructor
 */
export function EXTERNAL_ATTACKER_DATA_GET () {
  return axios({
    method: 'post',
    timeout: requestTimeout,
    url: dataGetUrl,
    headers: { 'Content-Type': 'application/json' },
    data: {
      aggs: {
        2: {
          terms: {
            field: 'client.ip',
            order: {
              _count: 'desc'
            },
            size: 99
          },
          aggs: {
            3: {
              terms: {
                field: 'client.ip',
                order: {
                  _count: 'desc'
                },
                size: 5
              }
            }
          }
        }
      },
      size: 0,
      stored_fields: [
        '*'
      ],
      script_fields: {},
      docvalue_fields: [
        {
          field: '@timestamp',
          format: 'date_time'
        },
        {
          field: 'eve.flow.end',
          format: 'date_time'
        },
        {
          field: 'eve.flow.start',
          format: 'date_time'
        },
        {
          field: 'eve.stats.detect.engines.last_reload',
          format: 'date_time'
        },
        {
          field: 'eve.timestamp',
          format: 'date_time'
        },
        {
          field: 'eve.tls.notafter',
          format: 'date_time'
        },
        {
          field: 'eve.tls.notbefore',
          format: 'date_time'
        },
        {
          field: 'event.created',
          format: 'date_time'
        },
        {
          field: 'event.end',
          format: 'date_time'
        },
        {
          field: 'event.ingested',
          format: 'date_time'
        },
        {
          field: 'event.start',
          format: 'date_time'
        },
        {
          field: 'file.accessed',
          format: 'date_time'
        },
        {
          field: 'file.created',
          format: 'date_time'
        },
        {
          field: 'file.ctime',
          format: 'date_time'
        },
        {
          field: 'file.mtime',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'package.installed',
          format: 'date_time'
        },
        {
          field: 'process.parent.start',
          format: 'date_time'
        },
        {
          field: 'process.start',
          format: 'date_time'
        },
        {
          field: 'tls.client.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.client.not_before',
          format: 'date_time'
        },
        {
          field: 'tls.client.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.client.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'tls.server.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.server.not_before',
          format: 'date_time'
        },
        {
          field: 'tls.server.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'tls.server.x509.not_before',
          format: 'date_time'
        }
      ],
      _source: {
        excludes: []
      },
      query: {
        bool: {
          must: [
            {
              match_all: {}
            }
          ],
          filter: [
            {
              match_phrase: {
                'eve.event_type': {
                  query: 'alert'
                }
              }
            },
            {
              range: {
                '@timestamp': {
                  gte: new Date().getTime() - (time),
                  lte: new Date().getTime(),
                  format: 'epoch_millis'
                }
              }
            }
          ],
          should: [],
          must_not: [
            {
              match_phrase: {
                'client.as.system': {
                  query: 'private'
                }
              }
            }
          ]
        }
      }
    }
  })
}

/**
 * 事实告警
 * @returns {AxiosPromise}
 * @constructor
 */
export function REAL_TIME_ALARM_GET () {
  return axios({
    method: 'post',
    timeout: requestTimeout,
    url: dataGetUrl,
    headers: { 'Content-Type': 'application/json' },
    data: {
      version: true,
      size: 10,
      sort: [
        {
          firstPacket: {
            order: 'desc',
            unmapped_type: 'boolean'
          }
        }
      ],
      aggs: {
        2: {
          date_histogram: {
            field: 'firstPacket',
            fixed_interval: '5m',
            time_zone: 'Asia/Shanghai',
            min_doc_count: 1
          }
        }
      },
      stored_fields: [
        '*'
      ],
      script_fields: {},
      docvalue_fields: [
        {
          field: '@timestamp',
          format: 'date_time'
        },
        {
          field: 'cert.notAfter',
          format: 'date_time'
        },
        {
          field: 'cert.notBefore',
          format: 'date_time'
        },
        {
          field: 'event.created',
          format: 'date_time'
        },
        {
          field: 'event.end',
          format: 'date_time'
        },
        {
          field: 'event.ingested',
          format: 'date_time'
        },
        {
          field: 'event.start',
          format: 'date_time'
        },
        {
          field: 'file.accessed',
          format: 'date_time'
        },
        {
          field: 'file.created',
          format: 'date_time'
        },
        {
          field: 'file.ctime',
          format: 'date_time'
        },
        {
          field: 'file.mtime',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'firstPacket',
          format: 'date_time'
        },
        {
          field: 'lastPacket',
          format: 'date_time'
        },
        {
          field: 'package.installed',
          format: 'date_time'
        },
        {
          field: 'process.parent.start',
          format: 'date_time'
        },
        {
          field: 'process.start',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.from',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.till',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.org_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.rec_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.ref_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.xmt_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.nextUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.revoketime',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.thisUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.pe.compile_ts',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_accessed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_changed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_created',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_modified',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.snmp.up_since',
          format: 'date_time'
        },
        {
          field: 'zeek.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_after',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_before',
          format: 'date_time'
        }
      ],
      _source: {
        excludes: []
      },
      query: {
        bool: {
          must: [
            {
              query_string: {
                analyze_wildcard: true,
                query: 'event.dataset:notice',
                default_field: '*',
                time_zone: 'Asia/Shanghai'
              }
            }
          ],
          filter: [
            {
              range: {
                firstPacket: {
                  gte: new Date().getTime() - (time),
                  lte: new Date().getTime(),
                  format: 'epoch_millis'
                }
              }
            }
          ],
          should: [],
          must_not: []
        }
      },
      highlight: {
        pre_tags: [
          '@opensearch-dashboards-highlighted-field@'
        ],
        post_tags: [
          '@/opensearch-dashboards-highlighted-field@'
        ],
        fields: {
          '*': {}
        },
        fragment_size: 2147483647
      }
    }
  })
}

/**
 * 网络异常事件
 * @returns {AxiosPromise}
 * @constructor
 */
export function EXCEPTION_EVENT_DATA_GET () {
  return axios({
    method: 'post',
    timeout: requestTimeout,
    url: dataGetUrl,
    headers: { 'Content-Type': 'application/json' },
    data: {
      aggs: {
        2: {
          terms: {
            field: 'zeek.weird.name',
            order: {
              _count: 'desc'
            },
            size: 100
          }
        }
      },
      size: 0,
      stored_fields: [
        '*'
      ],
      script_fields: {},
      docvalue_fields: [
        {
          field: '@timestamp',
          format: 'date_time'
        },
        {
          field: 'cert.notAfter',
          format: 'date_time'
        },
        {
          field: 'cert.notBefore',
          format: 'date_time'
        },
        {
          field: 'event.created',
          format: 'date_time'
        },
        {
          field: 'event.end',
          format: 'date_time'
        },
        {
          field: 'event.ingested',
          format: 'date_time'
        },
        {
          field: 'event.start',
          format: 'date_time'
        },
        {
          field: 'file.accessed',
          format: 'date_time'
        },
        {
          field: 'file.created',
          format: 'date_time'
        },
        {
          field: 'file.ctime',
          format: 'date_time'
        },
        {
          field: 'file.mtime',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_after',
          format: 'date_time'
        },
        {
          field: 'file.x509.not_before',
          format: 'date_time'
        },
        {
          field: 'firstPacket',
          format: 'date_time'
        },
        {
          field: 'lastPacket',
          format: 'date_time'
        },
        {
          field: 'package.installed',
          format: 'date_time'
        },
        {
          field: 'process.parent.start',
          format: 'date_time'
        },
        {
          field: 'process.start',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.from',
          format: 'date_time'
        },
        {
          field: 'zeek.kerberos.till',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.org_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.rec_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.ref_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ntp.xmt_time',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.nextUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.revoketime',
          format: 'date_time'
        },
        {
          field: 'zeek.ocsp.thisUpdate',
          format: 'date_time'
        },
        {
          field: 'zeek.pe.compile_ts',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_accessed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_changed',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_created',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.times_modified',
          format: 'date_time'
        },
        {
          field: 'zeek.smb_files.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.snmp.up_since',
          format: 'date_time'
        },
        {
          field: 'zeek.ts',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_after',
          format: 'date_time'
        },
        {
          field: 'zeek.x509.certificate_not_valid_before',
          format: 'date_time'
        }
      ],
      _source: {
        excludes: []
      },
      query: {
        bool: {
          must: [
            {
              query_string: {
                analyze_wildcard: true,
                default_field: '*',
                query: '*',
                time_zone: 'Asia/Shanghai'
              }
            },
            {
              match_all: {}
            },
            {
              query_string: {
                query: 'event.dataset:weird',
                analyze_wildcard: true,
                time_zone: 'Asia/Shanghai'
              }
            }
          ],
          filter: [
            {
              range: {
                firstPacket: {
                  gte: new Date().getTime() - (time),
                  lte: new Date().getTime(),
                  format: 'epoch_millis'
                }
              }
            }
          ],
          should: [],
          must_not: []
        }
      }
    }
  })
}
