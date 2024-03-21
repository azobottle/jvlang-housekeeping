import { AutoCrud } from "@hilla/react-crud";
import { UserEndpoint } from "Frontend/generated/endpoints";
import UserModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserModel";
import { GridColumn } from "@hilla/react-components/GridColumn.js";
import User from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/User";

export default function UserCrud() {
  return (
    <AutoCrud
      service={UserEndpoint}
      model={UserModel}
      gridProps={{
        visibleColumns: [
          "id",
          "username",
          "nickName",
          "description",
          "birthday",
          "otherNames",
          "wxOpenid",
          "rolenames",
        ],
        customColumns: [
          <GridColumn<User>
            key="rolenames"
            renderer={(obj) => {
              return (
                <span>
                  {String(
                    obj.item.roles?.map((it) => it?.role)?.join(", ") ??
                      "(没有任何角色)"
                  )}
                </span>
              );
            }}
            header="角色"
            autoWidth
          />,
        ],
      }}
      formProps={{
        visibleFields: ["nickName", "description", "birthday"],
      }}
    />
  );
}
