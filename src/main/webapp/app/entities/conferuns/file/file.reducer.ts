import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFile, defaultValue } from 'app/shared/model/conferuns/file.model';

export const ACTION_TYPES = {
  SEARCH_FILES: 'file/SEARCH_FILES',
  FETCH_FILE_LIST: 'file/FETCH_FILE_LIST',
  FETCH_FILE: 'file/FETCH_FILE',
  CREATE_FILE: 'file/CREATE_FILE',
  UPDATE_FILE: 'file/UPDATE_FILE',
  DELETE_FILE: 'file/DELETE_FILE',
  RESET: 'file/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFile>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FileState = Readonly<typeof initialState>;

// Reducer

export default (state: FileState = initialState, action): FileState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FILES):
    case REQUEST(ACTION_TYPES.FETCH_FILE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FILE):
    case REQUEST(ACTION_TYPES.UPDATE_FILE):
    case REQUEST(ACTION_TYPES.DELETE_FILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_FILES):
    case FAILURE(ACTION_TYPES.FETCH_FILE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILE):
    case FAILURE(ACTION_TYPES.CREATE_FILE):
    case FAILURE(ACTION_TYPES.UPDATE_FILE):
    case FAILURE(ACTION_TYPES.DELETE_FILE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FILES):
    case SUCCESS(ACTION_TYPES.FETCH_FILE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FILE):
    case SUCCESS(ACTION_TYPES.UPDATE_FILE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FILE):
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

const apiUrl = 'api/files';
const apiSearchUrl = 'api/_search/files';

// Actions

export const getSearchEntities: ICrudSearchAction<IFile> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FILES,
  payload: axios.get<IFile>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IFile> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FILE_LIST,
  payload: axios.get<IFile>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFile> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FILE,
    payload: axios.get<IFile>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FILE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FILE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFile> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FILE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
