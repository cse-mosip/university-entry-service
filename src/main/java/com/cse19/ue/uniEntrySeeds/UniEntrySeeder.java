package com.cse19.ue.uniEntrySeeds;

import com.cse19.ue.model.*;
import com.cse19.ue.repository.EntryLogRepository;
import com.cse19.ue.repository.EntryPlaceRepository;
import com.cse19.ue.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UniEntrySeeder implements CommandLineRunner {
    private GuestRepository guestRepository;
    private EntryPlaceRepository entryPlaceRepository;
    private EntryLogRepository entryLogRepository;

    @Autowired
    public UniEntrySeeder(GuestRepository guestRepository, EntryPlaceRepository entryPlaceRepository,
                          EntryLogRepository entryLogRepository) {
        this.guestRepository = guestRepository;
        this.entryPlaceRepository = entryPlaceRepository;
        this.entryLogRepository = entryLogRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedGuestData();
        seedEntryPlaceData();
        seedEntryLogData();
    }

    private void seedGuestData() {
        if (guestRepository.findByNIC("123456789X").isEmpty()) {
            Guest guest1 = Guest.builder()
                    .title(Title.MR)
                    .name("John Doe")
                    .phoneNumber("1234567890")
                    .NIC("123456789X")
                    .gender(Gender.MALE)
                    .inviterIndex("190623V")
                    .timestamp(LocalDateTime.now())
                    .approverEmail("security@gamil.com")
                    .build();
            guestRepository.save(guest1);
        }
        if (guestRepository.findByNIC("987654321Y").isEmpty()) {
            Guest guest2 = Guest.builder()
                    .title(Title.MS)
                    .name("Jane Smith")
                    .phoneNumber("9876543210")
                    .NIC("987654321Y")
                    .gender(Gender.FEMALE)
                    .inviterIndex("190623V")
                    .timestamp(LocalDateTime.now())
                    .approverEmail("security@gamil.com")
                    .build();


            guestRepository.save(guest2);
        }
    }

    private void seedEntryPlaceData() {
        if (entryPlaceRepository.findByName("Main Entrance").isEmpty()) {
            EntryPlace entryPlace1 = EntryPlace.builder()
                    .name("Main Entrance")
                    .build();
            entryPlaceRepository.save(entryPlace1);
        }
        if (entryPlaceRepository.findByName("Back Gate").isEmpty()) {
            EntryPlace entryPlace2 = EntryPlace.builder()
                    .name("Back Gate")
                    .build();

            entryPlaceRepository.save(entryPlace2);
        }
    }

    private void seedEntryLogData() {

        EntryPlace entryPlace = entryPlaceRepository.findEntryPlaceByName("Main Entrance");
        if (entryPlaceRepository.findByName("Main Entrance").isEmpty()) {
            UniversityEntryLog entryLog1 = UniversityEntryLog.builder()
                    .index("190623V")
                    .state(EntryState.IN)
                    .timestamp(LocalDateTime.now())
                    .approverEmail("security@gmail.com")
                    .entryPlace(entryPlace)
                    .build();

            entryLogRepository.save(entryLog1);
        }
    }

}
