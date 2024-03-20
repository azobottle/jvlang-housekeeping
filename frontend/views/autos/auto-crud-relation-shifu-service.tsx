import { AutoCrud } from "@hilla/react-crud";
import { RelationShifuServiceEndpoint } from "Frontend/generated/endpoints";
import RelationShifuServiceModel from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/RelationShifuServiceModel";
import { GridColumn } from "@hilla/react-components/GridColumn.js";

export default function RelationShifuServiceEndpointCrud() {
  return (
    <AutoCrud<RelationShifuServiceModel>
      service={RelationShifuServiceEndpoint}
      model={RelationShifuServiceModel}
      gridProps={{
        visibleColumns: ["shifuId", "serviceId", "shifuName", "serviceName"],
        customColumns: [
          <GridColumn<RelationShifuServiceModel>
            key="shifuName"
            renderer={(obj) => {
              return (
                <span>{String(obj.item.shifu?.nickName ?? "师傅不存在")}</span>
              );
            }}
            header="师傅名"
            autoWidth
          />,
          <GridColumn<RelationShifuServiceModel>
            key="serviceName"
            renderer={(obj) => {
              return (
                <span>{String(obj.item.service?.name ?? "服务不存在")}</span>
              );
            }}
            header="服务名"
            autoWidth
          />,
        ],
      }}
      formProps={{
        visibleFields: ["shifuId", "serviceId"],
      }}
    />
  );
}
