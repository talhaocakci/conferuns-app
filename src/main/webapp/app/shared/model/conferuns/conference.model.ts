import { Moment } from 'moment';
import { IScheduleItem } from 'app/shared/model/conferuns/schedule-item.model';
import { IPlace } from 'app/shared/model/conferuns/place.model';
import { ITalk } from 'app/shared/model/conferuns/talk.model';

export const enum ConferenceTopic {
  SOFTWARE_ENGINEERING = 'SOFTWARE_ENGINEERING',
  FINANCE = 'FINANCE',
  SPORTS = 'SPORTS'
}

export const enum Language {
  ENGLISH = 'ENGLISH',
  TURKISH = 'TURKISH',
  FRENCH = 'FRENCH',
  SPANISH = 'SPANISH'
}

export interface IConference {
  id?: number;
  conferenceId?: number;
  mainName?: string;
  subName?: string;
  mainTopic?: ConferenceTopic;
  subTopic?: string;
  description?: string;
  isFree?: boolean;
  language?: Language;
  startDate?: Moment;
  endDate?: Moment;
  lastTalkSubmissionTime?: Moment;
  scheduleItems?: IScheduleItem[];
  places?: IPlace[];
  talks?: ITalk[];
}

export const defaultValue: Readonly<IConference> = {
  isFree: false
};
