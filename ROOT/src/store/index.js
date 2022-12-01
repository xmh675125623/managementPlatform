import Vue from 'vue'
import Vuex from 'vuex'

import d2admin from './modules/d2admin'
import plat from './modules/plat'
import firewall from './modules/firewall'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    d2admin,
    plat,
    firewall
  }
})
