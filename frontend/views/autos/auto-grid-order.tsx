import { AutoGrid } from "@hilla/react-crud";
import { OrderEndpoint } from "Frontend/generated/endpoints";
import Order0Model from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/Order0Model";
import {GridColumn} from "@hilla/react-components/GridColumn.js";

export default function OrderGrid() {
  return (
    <AutoGrid
      service={OrderEndpoint}
      model={Order0Model}
      visibleColumns={ [
          "customerId",
          "shifuId",
          "serviceId",
          "customerName",
          "shifuName",
          "serviceName",
          "overEventDesc",
          "orderStatusDesc",
      ]}
      customColumns={[
          <GridColumn<Order0Model>
              key="customerName"
              renderer={(obj) => {
                  return <span>{String(obj.item.customer?.nickName ?? "客户不存在")}</span>;
              }}
              header="客户名"
              autoWidth
          />,
          <GridColumn<Order0Model>
              key="shifuName"
              renderer={(obj) => {
                  return <span>{String(obj.item.shifu?.nickName ?? "师傅不存在")}</span>;
              }}
              header="师傅名"
              autoWidth
          />,
          <GridColumn<Order0Model>
              key="serviceName"
              renderer={(obj) => {
                  return <span>{String(obj.item.service?.name ?? "服务不存在")}</span>;
              }}
              header="服务名"
              autoWidth
          />,
      ]}
    />
  );
}
