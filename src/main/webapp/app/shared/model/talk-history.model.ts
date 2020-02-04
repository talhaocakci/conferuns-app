import { Moment } from 'moment';

export interface ITalkHistory {
  id?: number;
  date?: Moment;
  totalAudience?: number;
  totalTechnicalPoints?: number;
  averageTechnicalPoints?: number;
  totalSpeakingPoints?: number;
  averageSpeakingPoints?: number;
  totalExcitementPoints?: number;
  averageExcitementPoints?: number;
  talkId?: number;
  presenterId?: number;
}

export const defaultValue: Readonly<ITalkHistory> = {};
