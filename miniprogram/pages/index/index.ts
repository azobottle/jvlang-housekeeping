// index.ts
import { IEventTrigged } from "../../utils/event"

const current_vroute_storage_key = 'jvlang_housekeeping:page:index:current_vroute' as const

type CurrentVrouteStroage = {
  current_vroute: string
}

Component({
  data: {
    current_vroute: 'home',
    lazy_loading_not_home: false,
    header_title: '',
    user_info: { a: 1 },
  },
  observers: {
    current_vroute(current_vroute: string) {
      let header_title: string;
      switch (current_vroute) {
        case "shop":
          header_title = "商店"
          break
        case "mine":
          header_title = "我的"
          break;
        default:
          header_title = '';
          break;
      }
      this.setData({
        header_title
      })
      if (!current_vroute.startsWith('home') && !this.data.lazy_loading_not_home) {
        this.setData({
          lazy_loading_not_home: true
        }, () => {
          console.debug('lazy_loading_not_home on change current_vroute')
        })
      }
      wx.setStorage<CurrentVrouteStroage>({
        key: current_vroute_storage_key,
        data: {
          current_vroute
        }
      })
        .then(() => console.debug('Storage current_vroute : ', current_vroute))
        .catch(err => console.error('Failed set storage for current_vroute : ' + current_vroute, err))
    }
  },
  lifetimes: {
    ready() {
      wx.getStorage<CurrentVrouteStroage>({ key: current_vroute_storage_key })
        .then((value) => {
          const { current_vroute } = value.data
          if (current_vroute) {
            this.setData({
              current_vroute
            })
          }
        })
        .catch(err => console.debug("Failed on load current_vroute , maybe first launch or clear storage ...", err))
      // lazy loading
      setTimeout(() => {
        if (!this.data.lazy_loading_not_home) {
          this.setData({
            lazy_loading_not_home: true
          }, () => {
            console.debug('lazy_loading_not_home after timeout')
          })
        }
      }, 1000)
    }
  },
  methods: {
    async on_switch_vroute(e: IEventTrigged<'switch_vroute', { vroute: string }>) {
      this.setData({
        current_vroute: e.detail.vroute
      })
    },
  },
})
