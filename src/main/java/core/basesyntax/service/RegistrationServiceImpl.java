package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin().length() < 6) {
            throw new RegistrationException("User login is too short");
        }

        if (user.getPassword().length() < 6) {
            throw new RegistrationException("User password is too short");
        }

        if (user.getAge() < 18) {
            throw new RegistrationException("User is too young");
        }

        for (User userFromList : Storage.people) {
            if (userFromList.getLogin().equals(user.getLogin())) {
                return null;
            }
        }

        storageDao.add(user);
        return user;
    }
}
