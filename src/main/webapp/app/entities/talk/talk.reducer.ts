import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITalk, defaultValue } from 'app/shared/model/talk.model';

export const ACTION_TYPES = {
  FETCH_TALK_LIST: 'talk/FETCH_TALK_LIST',
  FETCH_TALK: 'talk/FETCH_TALK',
  CREATE_TALK: 'talk/CREATE_TALK',
  UPDATE_TALK: 'talk/UPDATE_TALK',
  DELETE_TALK: 'talk/DELETE_TALK',
  RESET: 'talk/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITalk>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TalkState = Readonly<typeof initialState>;

// Reducer

export default (state: TalkState = initialState, action): TalkState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TALK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TALK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TALK):
    case REQUEST(ACTION_TYPES.UPDATE_TALK):
    case REQUEST(ACTION_TYPES.DELETE_TALK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TALK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TALK):
    case FAILURE(ACTION_TYPES.CREATE_TALK):
    case FAILURE(ACTION_TYPES.UPDATE_TALK):
    case FAILURE(ACTION_TYPES.DELETE_TALK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TALK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TALK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TALK):
    case SUCCESS(ACTION_TYPES.UPDATE_TALK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TALK):
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

const apiUrl = 'api/talks';

// Actions

export const getEntities: ICrudGetAllAction<ITalk> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TALK_LIST,
  payload: axios.get<ITalk>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITalk> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TALK,
    payload: axios.get<ITalk>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITalk> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TALK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITalk> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TALK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITalk> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TALK,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
