import { AutoCrud } from "@hilla/react-crud";
import { RoleEndpoint } from "Frontend/generated/endpoints";
import RoleModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/RoleModel";

export default function RoleCrud() {
  return <AutoCrud service={RoleEndpoint} model={RoleModel} />;
}
