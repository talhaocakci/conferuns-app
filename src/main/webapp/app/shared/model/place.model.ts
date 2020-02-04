import { IRoom } from 'app/shared/model/room.model';
import { IConference } from 'app/shared/model/conference.model';

export interface IPlace {
  id?: number;
  placeId?: number;
  name?: string;
  country?: string;
  state?: string;
  city?: string;
  district?: string;
  street?: string;
  doorNo?: string;
  rooms?: IRoom[];
  conferences?: IConference[];
}

export const defaultValue: Readonly<IPlace> = {};
