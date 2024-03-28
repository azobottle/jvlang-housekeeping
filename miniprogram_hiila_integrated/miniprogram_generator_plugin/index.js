import Plugin from "@hilla/generator-typescript-core/Plugin.js";

class MiniprogramPlugin extends Plugin {
  static MODEL_PLUGIN_FILE_TAGS = "MODEL_PLUGIN_FILE_TAGS";
  // #tags = /* @__PURE__ */ new WeakMap();

  // @ts-ignore
  get path() {
    return "@jvlang-housekeeping/miniprogram_generator_plugin";
  }
  async execute(storage) {
    this.logger.info('Miniprogram generator executing...')
    try {

    } finally {
      this.logger.info('Miniprogram generator finished .')
    }
  }
}
export {
  MiniprogramPlugin as default
};