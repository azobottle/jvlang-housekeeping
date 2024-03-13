// index.ts
import { IEventTrigged } from "../../utils/event"

const current_vroute_storage_key = 'jvlang_housekeeping:page:index:current_vroute' as const

type CurrentVrouteStroage = {
  current_vroute: string
}

Component({
  data: {
    current_vroute: 'home',
  },
  observers: {
    current_vroute(current_vroute: string) {
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
