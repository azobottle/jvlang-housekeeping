import { AutoCrud } from "@hilla/react-crud";
import { UserRoleEndpoint } from "Frontend/generated/endpoints";
import UserRoleModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserRoleModel";
import { GridColumn } from "@hilla/react-components/GridColumn.js";
import UserRole from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserRole";

export default function UserRoleCrud() {
  return (
    <AutoCrud<UserRoleModel>
      service={UserRoleEndpoint}
      model={UserRoleModel}
      gridProps={{
        visibleColumns: ["userId", "role", "user_username", "user_nickname"],
        customColumns: [
          <GridColumn<UserRole>
            key="user_username"
            renderer={(obj) => {
              const user = obj.item.user;
              return (
                <span>
                  {String(user == undefined ? "(用户不存在)" : user.username)}
                </span>
              );
            }}
            header="用户名"
            autoWidth
          />,
          <GridColumn<UserRole>
            key="user_nickname"
            renderer={(obj) => {
              const user = obj.item.user;
              return (
                <span>
                  {String(user == undefined ? "(用户不存在)" : user.nickName)}
                </span>
              );
            }}
            header="昵称"
            autoWidth
          />,
        ],
      }}
      formProps={{
        visibleFields: ["userId", "role"],
      }}
    />
  );
}
