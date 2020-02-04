import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITalkTag, defaultValue } from 'app/shared/model/talk-tag.model';

export const ACTION_TYPES = {
  FETCH_TALKTAG_LIST: 'talkTag/FETCH_TALKTAG_LIST',
  FETCH_TALKTAG: 'talkTag/FETCH_TALKTAG',
  CREATE_TALKTAG: 'talkTag/CREATE_TALKTAG',
  UPDATE_TALKTAG: 'talkTag/UPDATE_TALKTAG',
  DELETE_TALKTAG: 'talkTag/DELETE_TALKTAG',
  RESET: 'talkTag/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITalkTag>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TalkTagState = Readonly<typeof initialState>;

// Reducer

export default (state: TalkTagState = initialState, action): TalkTagState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TALKTAG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TALKTAG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TALKTAG):
    case REQUEST(ACTION_TYPES.UPDATE_TALKTAG):
    case REQUEST(ACTION_TYPES.DELETE_TALKTAG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TALKTAG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TALKTAG):
    case FAILURE(ACTION_TYPES.CREATE_TALKTAG):
    case FAILURE(ACTION_TYPES.UPDATE_TALKTAG):
    case FAILURE(ACTION_TYPES.DELETE_TALKTAG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TALKTAG_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TALKTAG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TALKTAG):
    case SUCCESS(ACTION_TYPES.UPDATE_TALKTAG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TALKTAG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/talk-tags';

// Actions

export const getEntities: ICrudGetAllAction<ITalkTag> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TALKTAG_LIST,
  payload: axios.get<ITalkTag>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITalkTag> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TALKTAG,
    payload: axios.get<ITalkTag>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITalkTag> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TALKTAG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITalkTag> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TALKTAG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITalkTag> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TALKTAG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
