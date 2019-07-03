import { ITalkHistory } from 'app/shared/model/conferuns/talk-history.model';

export interface IPresenter {
  id?: number;
  presenterId?: number;
  totalTechnicalPoints?: number;
  averageTechnicalPoints?: number;
  totalSpeakingPoints?: number;
  averageSpeakingPoints?: number;
  totalExcitementPoints?: number;
  averageExcitementPoints?: number;
  userId?: number;
  talks?: ITalkHistory[];
}

export const defaultValue: Readonly<IPresenter> = {};
