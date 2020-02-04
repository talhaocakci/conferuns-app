import { Moment } from 'moment';

export interface IScheduleItem {
  id?: number;
  fromTime?: Moment;
  tillTime?: Moment;
  talkId?: number;
  conferenceId?: number;
}

export const defaultValue: Readonly<IScheduleItem> = {};
