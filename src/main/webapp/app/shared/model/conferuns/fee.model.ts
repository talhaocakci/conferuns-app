import { Moment } from 'moment';

export const enum SpecialParticipantType {
  STUDENT = 'STUDENT',
  DISABLE = 'DISABLE',
  WOMAN = 'WOMAN',
  VETERAN = 'VETERAN'
}

export interface IFee {
  id?: number;
  conferenceId?: number;
  feeLabel?: string;
  fromTime?: Moment;
  tillTime?: Moment;
  price?: number;
  specialTo?: SpecialParticipantType;
}

export const defaultValue: Readonly<IFee> = {};
