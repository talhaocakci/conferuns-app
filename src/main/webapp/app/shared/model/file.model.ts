import { IFileReview } from 'app/shared/model/file-review.model';
import { FileStatus } from 'app/shared/model/enumerations/file-status.model';

export interface IFile {
  id?: number;
  name?: string;
  path?: string;
  type?: string;
  status?: FileStatus;
  reviews?: IFileReview[];
  talkId?: number;
}

export const defaultValue: Readonly<IFile> = {};
