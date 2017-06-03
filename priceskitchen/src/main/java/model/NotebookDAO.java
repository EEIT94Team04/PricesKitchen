package model;

import java.util.List;

public interface NotebookDAO {

	public void insert(NotebookBean bean);

	public void update(NotebookBean bean);

	public void delete(Integer note_id);
	
	public NotebookBean findByPrimaryKey(Integer note_id);

	public List<NotebookBean> getAll();
}
