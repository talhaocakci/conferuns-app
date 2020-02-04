export interface IRoom {
  id?: number;
  roomId?: number;
  roomName?: string;
  floor?: number;
  capacity?: number;
  placeId?: number;
}

export const defaultValue: Readonly<IRoom> = {};
