import Plugin from "@hilla/generator-typescript-core/Plugin.js";
import ts from "typescript";
const { createSourceFile } = ts;

import fs from "fs/promises"

class MiniprogramPlugin extends Plugin {
  get path() {
    return "@jvlang-housekeeping/miniprogram_generator_plugin";
  }
  /**
   * @param {import("@hilla/generator-typescript-core/SharedStorage").default} storage 
   */
  async execute(storage) {
    const { logger } = this
    const outdir = "../../miniprogram/generated/"
    const plugin_src_dir = "./miniprogram_hiila_integrated/miniprogram_generator_plugin";
    logger.debug('Miniprogram generator executing...')
    try {
      const { sources } = storage;
      logger.debug([...sources].map(it => it.fileName).sort(), 'Exist sources : ');
      const new_sources = sources.map((it) => {
        const { fileName } = it;
        if (fileName == 'connect-client.default.ts') {
          return false
        }
        it.languageVariant
        return {
          ...it,
          fileName: outdir + it.fileName,
          moduleName: 'miniprogram'
        }
      }).filter(Boolean);
      sources.push(...[
        ...new_sources,
        ...(await Promise.all(
          [
            'connect-client.default.ts',
            '@hilla/frontend.ts'
          ].map(async (f) => {
            return createSourceFile(
              outdir + f,
              await fs.readFile(plugin_src_dir + "/" + f, "utf-8"),
              9 // ES2022
            )
          })
        ))
      ]);
    } finally {
      logger.debug('Miniprogram generator finished .')
    }
  }
}
export {
  MiniprogramPlugin as default
};