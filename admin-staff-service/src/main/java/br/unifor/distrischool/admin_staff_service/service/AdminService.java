package br.unifor.distrischool.admin_staff_service.service;

import br.unifor.distrischool.admin_staff_service.dto.AdminDTO;
import br.unifor.distrischool.admin_staff_service.model.Admin;
import br.unifor.distrischool.admin_staff_service.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        if (adminRepository.findByEmail(adminDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já está em uso.");
        }
        Admin admin = convertToEntity(adminDTO);

        // Em um ambiente real, a senha seria criptografada aqui.
        // admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);
        return convertToDto(savedAdmin);
    }

    @Transactional(readOnly = true)
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AdminDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin não encontrado com o ID: " + id));
        return convertToDto(admin);
    }

    @Transactional
    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin não encontrado com o ID: " + id));

        existingAdmin.setName(adminDTO.getName());
        existingAdmin.setEmail(adminDTO.getEmail());
        existingAdmin.setRole(adminDTO.getRole());

        if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
            existingAdmin.setPassword(adminDTO.getPassword());
        }

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return convertToDto(updatedAdmin);
    }

    @Transactional
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new EntityNotFoundException("Admin não encontrado com o ID: " + id);
        }
        adminRepository.deleteById(id);
    }

    private AdminDTO convertToDto(Admin admin) {
        return new AdminDTO(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole()
        );
    }

    private Admin convertToEntity(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setRole(adminDTO.getRole());
        admin.setPassword(adminDTO.getPassword());
        return admin;
    }
}