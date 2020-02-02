import { Moment } from 'moment';

export const enum FileReviewStatus {
  NEED_MORE_REVIEW = 'NEED_MORE_REVIEW',
  REJECTED = 'REJECTED',
  APPROVED = 'APPROVED'
}

export interface IFileReview {
  id?: number;
  date?: Moment;
  comment?: string;
  reviewer?: string;
  status?: FileReviewStatus;
  fileId?: number;
}

export const defaultValue: Readonly<IFileReview> = {};
