import { Moment } from 'moment';
import { IScheduleItem } from 'app/shared/model/schedule-item.model';
import { IPlace } from 'app/shared/model/place.model';
import { ITalk } from 'app/shared/model/talk.model';
import { ConferenceTopic } from 'app/shared/model/enumerations/conference-topic.model';
import { Language } from 'app/shared/model/enumerations/language.model';

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
