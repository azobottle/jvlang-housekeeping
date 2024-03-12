import { AutoCrud } from "@hilla/react-crud";
import { RelationUserRoleEndpoint } from "Frontend/generated/endpoints";
import RelationUserRoleModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/RelationUserRoleModel";
import { GridColumn } from "@hilla/react-components/GridColumn.js";

export default function RelationUserRoleCrud() {
  return (
    <AutoCrud<RelationUserRoleModel>
      service={RelationUserRoleEndpoint}
      model={RelationUserRoleModel}
      gridProps={{
        visibleColumns: ["userId", "roleId", "userName", "roleName"],
        customColumns: [
          <GridColumn<RelationUserRoleModel>
            key="userName"
            renderer={(obj) => {
              return (
                <span>{String(obj.item.user?.nickName ?? "用户不存在")}</span>
              );
            }}
            header="用户名"
            autoWidth
          />,
          <GridColumn<RelationUserRoleModel>
            key="roleName"
            renderer={(obj) => {
              return <span>{String(obj.item.role?.name ?? "角色不存在")}</span>;
            }}
            header="角色名"
            autoWidth
          />,
        ],
      }}
    />
  );
}
