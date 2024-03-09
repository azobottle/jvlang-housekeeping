export type IEvent<CurrentTargettDataset> = WechatMiniprogram.BaseEvent<
  CurrentTargettDataset,
  CurrentTargettDataset
>

export type IEventTrigged<T extends string, D> = {
  type: T
  detail: D
}