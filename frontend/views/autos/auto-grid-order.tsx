import { AutoGrid } from "@hilla/react-crud";
import { OrderEndpoint } from "Frontend/generated/endpoints";
import Order0Model from "Frontend/generated/com/jvlang/housekeeping/pojo/entity/Order0Model";
import { GridColumn } from "@hilla/react-components/GridColumn.js";
import { userPubInfo } from "Frontend/util/extfunc";

export default function OrderGrid() {
  return (
    <AutoGrid
      service={OrderEndpoint}
      model={Order0Model}
      visibleColumns={[
        "id",
        "createTime",
        "modifyTime",
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
            return (
              <span>
                {String(
                  userPubInfo(obj.item.valueOf(), "customerId")?.displayName ??
                    "客户不存在"
                )}
              </span>
            );
          }}
          header="客户名"
          autoWidth
        />,
        <GridColumn<Order0Model>
          key="shifuName"
          renderer={(obj) => {
            return (
              <span>
                {String(
                  userPubInfo(obj.item.valueOf(), "shifuId")?.displayName ??
                    "师傅不存在"
                )}
              </span>
            );
          }}
          header="师傅名"
          autoWidth
        />,
      ]}
    />
  );
}
