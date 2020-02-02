import { ITalk } from 'app/shared/model/conferuns/talk.model';

export interface ITalkParticipant {
  id?: number;
  checkedIn?: boolean;
  plannedToGo?: boolean;
  favorited?: boolean;
  talks?: ITalk[];
}

export const defaultValue: Readonly<ITalkParticipant> = {
  checkedIn: false,
  plannedToGo: false,
  favorited: false
};
