import {defineStore} from "pinia"

const useDataStore = defineStore("data", {
    state: () => {
        return {
            userId:'',
            adminId:'',
            nickname:'',
            avater:'',
            token:''
        }
    },
    //本地持久化(把数据存储到浏览器本地)
    persist: {
        enabled: true,//是否开启持久化
        strategies: [
            {
                key: 'data', //自定义Key值,存储到本地时的key
                storage: localStorage, // 选择存储方式：本地存储
            },
        ],
    }
})

export default useDataStore