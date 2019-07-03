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
  presenterId?: number;
  talkId?: number;
}

export const defaultValue: Readonly<ITalkHistory> = {};
