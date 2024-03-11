import { AutoCrud } from "@hilla/react-crud";
import { RelationUserRoleService } from "Frontend/generated/endpoints";
import RelationUserRole from "Frontend/generated/com/example/application/persistence/document/RelationUserRole/View1Model";

export default function RelationUserRoleCrud() {
  return (
    <AutoCrud
      service={RelationUserRoleService}
      model={RelationUserRole}
    />
  );
}
