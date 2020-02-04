import { Moment } from 'moment';
import { FileReviewStatus } from 'app/shared/model/enumerations/file-review-status.model';

export interface IFileReview {
  id?: number;
  date?: Moment;
  comment?: string;
  reviewer?: string;
  status?: FileReviewStatus;
  fileId?: number;
}

export const defaultValue: Readonly<IFileReview> = {};
