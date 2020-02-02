import { Moment } from 'moment';

export interface IScheduleItem {
  id?: number;
  fromTime?: Moment;
  tillTime?: Moment;
  conferenceId?: number;
  talkId?: number;
}

export const defaultValue: Readonly<IScheduleItem> = {};
