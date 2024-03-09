import { AutoCrud } from "@hilla/react-crud";
import { RoleService } from "Frontend/generated/endpoints";
import RoleModel from "Frontend/generated/com/example/application/persistence/document/RoleModel";

export default function RoleGrid() {
  return (
    <AutoCrud
      service={RoleService}
      model={RoleModel}
      gridProps={{ visibleColumns: ["name", "description"] }}
      formProps={{
        visibleFields: ["name", "description"],
        deleteButtonVisible: true,
      }}
    />
  );
}
