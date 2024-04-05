import {
  AbstractModel,
  DetachedModelConstructor,
  ObjectModel,
  Value,
} from "@hilla/form";
import {
  GridColumn,
  GridColumnElement,
} from "@hilla/react-components/GridColumn.js";
import { AutoCrud, CrudService } from "@hilla/react-crud";
import User from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/User";
import UserModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserModel";
import { UserEndpoint } from "Frontend/generated/endpoints";
import { ComponentType } from "react";
import { lazy } from "react";

export function MyAutoCrud<TModel extends AbstractModel>(options: {
  notDisplayColumns?: (keyof TModel)[];
  readOnlyColumns?: (keyof TModel)[];
  endpoint: CrudService<Value<TModel>>;
  model: DetachedModelConstructor<TModel>;
  customColumns?: {
    header: string;
    render:
      | {
          custom_type: "span";
          text: (m: TModel) => string;
        }
      | ComponentType<
          Readonly<{
            item: TModel;
            model: import("@vaadin/grid/src/vaadin-grid-mixin.js").GridItemModel<TModel>;
            original: GridColumnElement<TModel>;
          }>
        >
      | null
      | undefined;
  }[];
}) {
  let columns = [] as string[];
  let p = UserModel.prototype;
  while (p != null) {
    columns = [...Object.getOwnPropertyNames(p), ...columns];
    p = Object.getPrototypeOf(p);
    if (p == Object.getPrototypeOf({})) {
      break;
    }
  }
  columns = columns.filter(
    (it) =>
      ![
        "constructor",
        "toString",
        "valueOf",
        ...(options.notDisplayColumns ?? []),
      ].includes(it)
  );
  return (
    <AutoCrud
      service={options.endpoint}
      model={options.model}
      gridProps={{
        visibleColumns: columns,
        customColumns: options.customColumns?.map((it) => (
          <GridColumn<TModel>
            key={"key_" + it.header}
            renderer={(() => {
              if (it.render && "custom_type" in it.render) {
                switch (it.render.custom_type) {
                  case "span":
                    const _f1 = it.render.text;
                    return (obj) => <span>{String(_f1(obj.item))}</span>;
                  default:
                    throw new Error(
                      "Invalid render custom_type" + it.render.custom_type
                    );
                }
              }
              return it.render;
            })()}
            header={it.header}
            autoWidth
          />
        )),
      }}
      formProps={{
        visibleFields: columns.filter(
          (it) =>
            ![
              "id",
              "createTime",
              "modifyTime",
              "createUserId",
              "modifyUserId",
              "optimisticLocking",
              ...(options.readOnlyColumns ?? []),
            ].includes(it)
        ),
      }}
    />
  );
}
