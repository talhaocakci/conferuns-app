import { Moment } from 'moment';
import { SpecialParticipantType } from 'app/shared/model/enumerations/special-participant-type.model';

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
