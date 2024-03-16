// components/card/index.ts
Component({

  /**
   * 组件的属性列表
   */
  properties: {
    border_color: {
      type: String,
      value: ''
    },
    tag_top: {
      type: String,
      value: ''
    },
  },

  observers: {
    border_color() {
      this._flush_data()
    },
    tag_top() {
      this._flush_data()
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    class1: '',
    style1: '',
    style2: ''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    _flush_data() {
      const { border_color, tag_top } = this.properties
      this.setData({
        class1: border_color || tag_top ? 'has_border' : 'no_border',
        style1: border_color ? 'border-color: ' + border_color + ';' : '',
        style2: border_color ? 'background-color: ' + border_color + ';' : '',
      })
    }
  }
})