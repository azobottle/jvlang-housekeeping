import { AutoCrud } from "@hilla/react-crud";
import { UserRoleEndpoint } from "Frontend/generated/endpoints";
import UserRoleModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserRoleModel";
import { GridColumn } from "@hilla/react-components/GridColumn.js";
import UserRole from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/UserRole";
import { Upload } from "@hilla/react-components/Upload";
import { userPubInfo } from "Frontend/util/extfunc";

export default function UserRoleCrud() {
  const uploadHeader = {
    type: "rela_user_role",
    Authorization: localStorage.getItem("auth_token"),
  };
  return (
    <div className="p-m">
      <AutoCrud<UserRoleModel>
        service={UserRoleEndpoint}
        model={UserRoleModel}
        gridProps={{
          visibleColumns: [
            "id",
            "createTime",
            "modifyTime",
            "userId",
            "role",
            "username",
          ],
          customColumns: [
            <GridColumn<UserRole>
              key="username"
              renderer={(obj) => {
                return (
                  <span>
                    {String(
                      userPubInfo(obj.item, "userId")?.displayName ??
                        "(用户不存在)"
                    )}
                  </span>
                );
              }}
              header="用户名"
              autoWidth
            />,
          ],
        }}
        formProps={{
          visibleFields: ["userId", "role"],
        }}
      />
      <Upload target="/api/file/upload" headers={uploadHeader} />
    </div>
  );
}
