import { ITalk } from 'app/shared/model/conferuns/talk.model';

export interface ITalkTag {
  id?: number;
  tag?: string;
  talks?: ITalk[];
}

export const defaultValue: Readonly<ITalkTag> = {};
