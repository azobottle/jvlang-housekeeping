import { MyAutoCrud } from "./components/MyCrud";
import UserModel from "./generated/com/jvlang/housekeeping/pojo/entity/UserModel";
import {
  GoodCatalogEndpoint,
  UserEndpoint,
  UserRoleEndpoint,
} from "./generated/endpoints";
import UserRoleModel from "./generated/com/jvlang/housekeeping/pojo/entity/UserRoleModel";
import GoodCatalogModel from "./generated/com/jvlang/housekeeping/pojo/entity/GoodCatalogModel";

export default [
  {
    path: "/user",
    handle: { title: "用户" },
    element: (
      <MyAutoCrud
        model={UserModel}
        endpoint={UserEndpoint}
        notDisplayColumns={["encodedPassword"]}
        customColumns={[
          {
            header: "角色",
            render: {
              custom_type: "span",
              text(m) {
                return (
                  m.roles
                    .valueOf()
                    ?.map((it) => it?.role)
                    ?.join(", ") ?? "(没有任何角色)"
                );
              },
            },
          },
        ]}
      />
    ),
  },
  {
    path: "/user-role",
    handle: { title: "用户角色" },
    element: <MyAutoCrud model={UserRoleModel} endpoint={UserRoleEndpoint} />,
  },
  {
    path: "/good-catalog",
    handle: { title: "商品分类" },
    element: (
      <MyAutoCrud model={GoodCatalogModel} endpoint={GoodCatalogEndpoint} />
    ),
  },
] as const;
