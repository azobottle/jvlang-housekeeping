
import { AutoCrud } from "@hilla/react-crud";
import { RelationUserRoleDTOService } from "Frontend/generated/endpoints";
import RelationUserRoleDTOModel from "Frontend/generated/com/example/application/endpoint/dto/RelationUserRoleDTOModel";

export default function RelationUserRoleCrud() {
  return (
    <AutoCrud
      service={RelationUserRoleDTOService}
      model={RelationUserRoleDTOModel}
      gridProps={{ visibleColumns: ["userName", "phoneNumber", "roleName"] }}
      formProps={{
        visibleFields: ["userName", "roleName"],
        deleteButtonVisible: true,
      }}
    />
  );
}
