import { AutoCrud } from "@hilla/react-crud";
import { RoleService } from "Frontend/generated/endpoints";
import RoleModel from "Frontend/generated/com/example/application/persistence/document/RoleModel";

export default function RoleCrud() {
  return <AutoCrud service={RoleService} model={RoleModel} />;
}
