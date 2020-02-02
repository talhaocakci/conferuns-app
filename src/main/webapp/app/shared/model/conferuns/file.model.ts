import { IFileReview } from 'app/shared/model/conferuns/file-review.model';

export const enum FileStatus {
  DRAFT = 'DRAFT',
  IN_REVIEW = 'IN_REVIEW',
  NEED_MORE_REVIEW = 'NEED_MORE_REVIEW',
  REJECTED = 'REJECTED',
  APPROVED = 'APPROVED'
}

export interface IFile {
  id?: number;
  name?: string;
  path?: string;
  type?: string;
  status?: FileStatus;
  talkId?: number;
  reviews?: IFileReview[];
}

export const defaultValue: Readonly<IFile> = {};
