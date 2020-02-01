package auth.example.akoum.services;

import java.util.List;

import auth.example.akoum.entities.User;

public interface IServiceUser {

	public void register(User u);
	public List<User> getAll();
	public void delete(int id);
    public User findById(int id);
    public User finByUsername(String username);
}
