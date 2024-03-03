package com.example.mainfile.repository;

import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.RoomEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public void testFindByHotel() {

        HotelEntity hotel = new HotelEntity();

        entityManager.persist(hotel);

        RoomEntity room = new RoomEntity();
        room.setHotel(hotel);

        entityManager.persist(room);

        List<RoomEntity> rooms = roomRepository.findByHotel(hotel);

        assertThat(rooms).hasSize(1);
        assertThat(rooms).containsExactly(room);
    }




    @Test
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public void testFindByHotelId() {

        HotelEntity hotel = new HotelEntity();

        entityManager.persist(hotel);

        RoomEntity room = new RoomEntity();
        room.setHotel(hotel);

        entityManager.persist(room);

        List<RoomEntity> rooms = roomRepository.findByHotelId(hotel.getId());

        assertThat(rooms).hasSize(1);
        assertThat(rooms.get(0)).isEqualTo(room);
    }
}

