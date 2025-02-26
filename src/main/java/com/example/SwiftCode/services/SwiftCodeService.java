package com.example.SwiftCode.services;

import com.example.SwiftCode.dto.CountryDTO;
import com.example.SwiftCode.dto.OfficeDTO;
import com.example.SwiftCode.exceptions.ItemNotFoundException;
import com.example.SwiftCode.models.Branch;
import com.example.SwiftCode.models.Headquarter;
import com.example.SwiftCode.repository.BranchRepository;
import com.example.SwiftCode.repository.HeadquarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SwiftCodeService {
    private final HeadquarterRepository headquarterRepository;
    private final BranchRepository branchRepository;
    private final HeadquarterDTOMapper headquarterDTOMapper;
    private final CountryISO2CodeMapper countryISO2CodeMapper;
    private final OfficeDTOMapper officeDTOMapper;
    private final BranchDTOMapper branchDTOMapper;

    @Autowired
    public SwiftCodeService(
            HeadquarterRepository headquarterRepository,
            BranchRepository branchRepository,
            HeadquarterDTOMapper headquarterDTOMapper,
            CountryISO2CodeMapper countryISO2CodeMapper,
            OfficeDTOMapper officeDTOMapper, BranchDTOMapper branchDTOMapper) {
        this.headquarterRepository = headquarterRepository;
        this.branchRepository = branchRepository;
        this.headquarterDTOMapper = headquarterDTOMapper;
        this.countryISO2CodeMapper = countryISO2CodeMapper;
        this.officeDTOMapper = officeDTOMapper;
        this.branchDTOMapper = branchDTOMapper;
    }
    public Object findBySwiftCode(String swiftCode){
            if (swiftCode.contains("XXX")) {
                return headquarterRepository.findBySwiftCode(swiftCode)
                        .map(headquarterDTOMapper::apply)
                        .orElseThrow(() -> new ItemNotFoundException(swiftCode + " number Swift code not found in database"));
            } else {
                return branchRepository.findBySwiftCode(swiftCode)
                        .map(branchDTOMapper::apply)
                        .orElseThrow(() -> new ItemNotFoundException(swiftCode + " number Swift code not found in database"));
            }
        }

    public CountryDTO findByCountryISO2Code(String countryISO2Code){
        List<Headquarter> headquarters = headquarterRepository.findAllByCountryISO2(countryISO2Code);
        List<Branch> branches = branchRepository.findAllByCountryISO2(countryISO2Code);
        if(headquarters.isEmpty() && branches.isEmpty()){
            throw new ItemNotFoundException("No such country ISO2 code found");
        }
            return countryISO2CodeMapper.toCountryDTO(headquarters, branches);
    }

    public Object setNewSwiftCodeOffice(OfficeDTO officeDTO){
        if(officeDTO.getSwiftCode().contains("XXX")){
            return headquarterRepository.findBySwiftCode(officeDTO.getSwiftCode()).map(headquarter ->{
                throw new DataIntegrityViolationException("The stated headquarters with this Swift Code already exists");
            }).orElseGet( () -> {
                        Headquarter headquarter = officeDTOMapper.toHeadquarterOfficeMapper(officeDTO);
                        String branchSwiftCode = officeDTO.getSwiftCode().substring(0, 8);
                        Optional<Branch> existingBranches = branchRepository.findBySwiftCodeStartsWith(branchSwiftCode);
                        headquarter.setBranchesList(existingBranches.stream().collect(Collectors.toList()));
                        Headquarter savedHeadquarter = headquarterRepository.save(headquarter);
                        return savedHeadquarter;
                    }
            );
        }else {
            return branchRepository.findBySwiftCode(officeDTO.getSwiftCode()).map(branch -> {
                throw new DataIntegrityViolationException("The stated branch with this Swift Code already exists");
        }).orElseGet(() ->{
                String branchSwiftCode = officeDTO.getSwiftCode().substring(0, 8);
                Headquarter existingHeadquarter = headquarterRepository.findBySwiftCodeStartsWith(branchSwiftCode);
                Branch branch = officeDTOMapper.toBranchOfficeMapper(officeDTO);
                if(existingHeadquarter != null){
                    existingHeadquarter.setBranches(branch);
                    headquarterRepository.save(existingHeadquarter);
                    return existingHeadquarter;
                }else{
                    branchRepository.save(branch);
                    return branch;
                }
            });
        }
    }


    public boolean deleteSwiftCodeData(String swiftCode){
        boolean deleted = false;

        Optional<Headquarter> headquarterOpt = headquarterRepository.findBySwiftCode(swiftCode);
        if (headquarterOpt.isPresent()) {
            headquarterRepository.deleteById(headquarterOpt.get().getId());
            deleted = true;
        }

        Optional<Branch> branchOpt = branchRepository.findBySwiftCode(swiftCode);
        if (branchOpt.isPresent()) {
            branchRepository.deleteById(branchOpt.get().getId());
            deleted = true;
        }
        return deleted;

    }
    public List<Headquarter> getAll() {
        return headquarterRepository.findAll();
    }
    @Transactional
    public void saveDataFromList(List<Headquarter> headquarters, List<Branch> branches){
        for(Headquarter headquarter: headquarters){
            headquarterRepository.findBySwiftCode(headquarter.getSwiftCode()).ifPresentOrElse(headquarterExists -> {
            }, () ->{  headquarterRepository.save(headquarter);});
        }
        for(Branch branch: branches){
            branchRepository.findBySwiftCode(branch.getSwiftCode()).ifPresentOrElse(branchExists -> {}, () -> {
                String branchSwiftCode = branch.getSwiftCode().substring(0, 8);
                Headquarter existingHeadquarter = headquarterRepository.findBySwiftCodeStartsWith(branchSwiftCode);
                if(existingHeadquarter != null){
                    existingHeadquarter.setBranches(branch);
                    headquarterRepository.save(existingHeadquarter);
                }else{
                    branchRepository.save(branch);
                }
            });
        }
    }

}
