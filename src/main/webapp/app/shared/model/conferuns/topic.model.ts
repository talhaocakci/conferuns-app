export interface ITopic {
  id?: number;
  category?: string;
  subCategory?: string;
  topicName?: string;
}

export const defaultValue: Readonly<ITopic> = {};
