// index.ts
import { IEventTrigged } from "../../utils/event"

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
        key: 'jvlang_housekeeping:index:current_vroute',
        data: {
          current_vroute
        }
      })
    }
  },
  lifetimes: {
    ready() {
      wx.getStorage<CurrentVrouteStroage>({ key: 'jvlang_housekeeping:index:current_vroute' })
        .then((value) => {
          const { current_vroute } = value ? value.data : { current_vroute: undefined }
          if (current_vroute) {
            this.setData({
              current_vroute
            })
          }
        })
        .catch(err => console.debug("Error on load current_vroute", err))
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
