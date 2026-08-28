package cd.cc.service.impl;

import cd.cc.domain.po.Address;
import cd.cc.mapper.AddressMapper;
import cd.cc.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DELE
 * @since 2026-08-28
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
