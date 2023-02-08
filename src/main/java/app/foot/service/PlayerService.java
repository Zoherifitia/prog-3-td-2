package app.foot.service;

import app.foot.model.Player;
import app.foot.model.PlayerUpdate;
import app.foot.repository.PlayerRepository;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    public List<Player> getPlayers() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Player> createPlayers(List<Player> toCreate) {
        return repository.saveAll(toCreate.stream()
                        .map(mapper::toEntity)
                        .collect(Collectors.toUnmodifiableList())).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Player> updatePlayers(List<PlayerUpdate> toUpdate){
        List<PlayerEntity> playerEntities = toUpdate.stream()
                .map(this::relatedInfo)
                .toList();
        return repository.saveAll(playerEntities)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    private PlayerEntity relatedInfo(PlayerUpdate player) {
        PlayerEntity playerEntity = repository.getReferenceById(player.getId());
        if(player.getName() != null) {
            playerEntity.setName(player.getName());
        }
        if (player.getIsGuardian() != null) {
            playerEntity.setGuardian(player.getIsGuardian());
        }
        return playerEntity;
    }
}
