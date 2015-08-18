package com.javassf.basic.hibernate4;

import com.javassf.basic.utils.ExecuteQueryUtils;
import com.javassf.basic.utils.HibernateUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.data.domain.Page;

public abstract class QueryDaoImpl<T, ID extends Serializable> extends BaseQueryDaoImpl<T> implements QueryDao<T> {
	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	protected T get(ID id) {
		return (T) getSession().get(getEntityClass(), id);
	}

	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	public T findById(ID id) {
		Object entity = get(id);
		return (T) entity;
	}

	/**
	 * 添加
	 */
	public T save(T bean) {
		getSession().save(bean);
		return bean;
	}
	/**
	 * 修改
	 */
	public T update(T bean) {
		ClassMetadata cm = this.sessionFactory.getClassMetadata(getEntityClass());
		T po = (T) getSession().get(getEntityClass(), cm.getIdentifier(bean, null));
		//修改值
		updaterCopyToPersistentObject(bean, po, cm);
		return po;
	}

	/**
	 * 删除对应id的对象
	 * @param id
	 * @return
	 */
	public T deleteById(ID id) {
		Object entity = get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return (T) entity;
	}

	/**
	 * 分页查询
	 */
	public Page<T> getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		return findByCriteria(crit, pageNo, pageSize);
	}

	/**
	 * 
	 * @param eq
	 * @return
	 */
	public List<T> executeQueryList(ExecuteQueryUtils eq) {
		Query query = getSession().createQuery(eq.getHql());
		if (eq.getMaps() != null) {
			for (Map.Entry<String, Object> entry : eq.getMaps().entrySet()) {
				query.setParameter((String) entry.getKey(), entry.getValue());
			}
		}
		return query.list();
	}

	/**
	 * 通过ExecuteQueryUtils 执行hql
	 * @param eq
	 * @return
	 */
	public int executeQuery(ExecuteQueryUtils eq) {
		Query query = getSession().createQuery(eq.getHql());
		if (eq.getMaps() != null) {
			//遍历设置对应参数
			for (Map.Entry<String, Object> entry : eq.getMaps().entrySet()) {
				query.setParameter((String) entry.getKey(), entry.getValue());
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 执行hql
	 * @param hql
	 * @param value
	 * @return
	 */
	public int executeQuery(String hql, Object[] value) {
		Query query = getSession().createQuery(hql);
		if (value != null) {
			for (int i = 0; i < value.length; i++) {
				query.setParameter(i, value[i]);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 获取Criteria
	 * @return
	 */
	protected Criteria createCriteria() {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		return criteria;
	}

	private void updaterCopyToPersistentObject(T bean, T po, ClassMetadata cm) {
		//获取所有属性名
		String[] propNames = cm.getPropertyNames();
		//获取主键名称
		String identifierName = cm.getIdentifierPropertyName();
		
		//变量属性名
		for (String propName : propNames) {
			//若是主键则跳过
			if (propName.equals(identifierName))
				continue;
			try {
				//通过反射，获取对应字段的值
				Object value = HibernateUtils.getSimpleProperty(bean, propName);
				if (value == null) {
					continue;
				}
				//设置对应的值
				cm.setPropertyValue(po, propName, value);
			} catch (Exception e) {
				throw new RuntimeException("拷贝属性失败", e);
			}
		}
	}

	protected abstract Class<T> getEntityClass();
}