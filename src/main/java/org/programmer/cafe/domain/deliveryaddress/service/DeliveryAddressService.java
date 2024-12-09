package org.programmer.cafe.domain.deliveryaddress.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.deliveryaddress.entity.DeliveryAddress;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressRequest;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressResponse;
import org.programmer.cafe.domain.deliveryaddress.repository.DeliveryAddressRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    public DeliveryAddressService(final DeliveryAddressRepository deliveryRepository, DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    /**
     * 주어진 사용자 이메일을 기반으로 배송 주소 목록을 조회하고 변환하여 반환
     *
     * @param email 사용자 이메일 정보를 포함하는 DeliveryAddressRequest 객체
     * @return 사용자의 이메일로 조회된 배송 주소 목록의 DeliveryAddressResponse 객체 리스트
     * @throws BadRequestException 요청 객체가 null이거나 유효하지 않은 경우 발생
     */
    public List<DeliveryAddressResponse> getAddressByEmail(String email) {

        return deliveryAddressRepository
                .findByUserEmail(email)
                .stream()
                .map(address -> {
                    DeliveryAddressResponse response = new DeliveryAddressResponse();
                    response.setId(address.getId());
                    response.setAddress(address.getAddress());
                    response.setAddressDetail(address.getAddressDetail());
                    response.setName(address.getName());
                    response.setZipcode(address.getZipcode());
                    response.setUserId(address.getUser().getId());
                    response.setDefaultYn(address.isDefaultYn());
                    return response;
                })
                .toList();
    }

    public Long getUserIdById(Long Id) {
        return deliveryRepository.getUserIdById(Id);
    }

    /**
     * 배송 주소를 추가
     *
     * @param request 저장할 배송 주소 데이터
     * @return 주소가 성공적으로 저장되었으면 true 반환
     * @throws BadRequestException 요청이 유효하지 않거나 저장 작업에 실패한 경우 예외 발생
     */
    @Transactional
    public Boolean putAddress(DeliveryAddressRequest request) {

        validateRequest(request);

        return attemptSave(request);
    }

    /**
     * 주어진 배송 주소 요청 정보를 기반으로 주소 삭제 처리
     *
     * @param id 삭제할 배송 주소의 ID를 포함하는 DeliveryAddressRequest 객체
     * @return 삭제 성공 시 true를 반환
     * @throws BadRequestException 요청 유효성 검증 실패 또는 삭제 실패 시 발생
     */
    @Transactional
    public Boolean deleteAddress(Long id) {
        if (id == null) {
            throw new BadRequestException(ErrorCode.ADDRESS_NOT_EXIST);
        }
        return attemptDelete(id);
    }

    /**
     * 배송 주소를 업데이트
     *
     * @param request 업데이트할 배송 주소 데이터를 포함한 요청 객체
     * @return 업데이트가 성공하면 true 반환
     * @throws BadRequestException 요청이 유효하지 않거나 업데이트 작업이 실패한 경우 예외 발생
     */
    @Transactional
    public Boolean updateAddress(DeliveryAddressRequest request) {
        validateRequest(request);

        return attemptUpdate(request);
    }

    /**
     * 입력 요청 데이터를 검증
     *
     * @param request 검증할 배송 주소 데이터
     * @throws BadRequestException 요청이 null이거나 유효하지 않은 경우 예외 발생
     */
    private void validateRequest(DeliveryAddressRequest request) {
        if (request == null) {
            throw new BadRequestException(ErrorCode.BAD_REQUEST); // 400: 잘못된 요청
        }
    }

    /**
     * 배송 주소 저장을 시도하고 실패 시 예외를 처리
     *
     * @param request 저장할 배송 주소 데이터
     * @return 주소가 성공적으로 저장되었으면 true 반환
     * @throws BadRequestException 저장 작업이 실패한 경우 예외 발생
     */
    @Transactional
    protected Boolean attemptSave(DeliveryAddressRequest request) {
        if (deliveryAddressRepository.existsByUserIdAndDefaultYn(request.getUserId(), true)) {
            throw new BadRequestException(ErrorCode.DEFAULT_IS_ONLY);
        }

        int isSaved = deliveryRepository.saveDeliveryAddress(request);

        if (isSaved > 0) {
            return true;
        } else {
            throw new BadRequestException(ErrorCode.ADDRESS_SAVE_FAILED);
        }
    }


    /**
     * 배송 주소 업데이트를 시도하고 결과를 반환
     *
     * @param request 업데이트할 배송 주소 데이터를 포함한 요청 객체
     * @return 업데이트가 성공하면 true 반환
     * @throws BadRequestException 업데이트 작업이 실패한 경우 예외 발생
     */
    @Transactional
    protected Boolean attemptUpdate(DeliveryAddressRequest request) {

        DeliveryAddress existingAddress = deliveryRepository.findById(request.getId()).orElseThrow(() -> new BadRequestException(ErrorCode.ADDRESS_NOT_EXIST));
        int isUpdated = deliveryRepository.updateDeliveryAddress(request);
        if (isUpdated > 0) {
            return true;
        } else {
            throw new BadRequestException(ErrorCode.ADDRESS_UPDATE_FAILED);
        }
    }

    /**
     * 주어진 배송 주소 요청 정보를 기반으로 해당 주소를 삭제
     *
     * @param id 삭제할 배송 주소의 ID를 포함하는 DeliveryAddressRequest 객체
     * @return 삭제 성공 시 true를 반환
     * @throws BadRequestException 요청된 주소가 존재하지 않거나 삭제에 실패한 경우 발생
     */
    @Transactional
    protected Boolean attemptDelete(Long id) {
        int isDeleted = deliveryRepository.deleteDeliveryAddressById(id);
        if (isDeleted > 0) {
            return true;
        } else {
            throw new BadRequestException(ErrorCode.ADDRESS_DELETE_FAILED);
        }
    }

}
